package uma.caosd.cvltool;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import bvr.BaseModelHandle;
import bvr.ChoiceResolutuion;
import bvr.LinkExistence;
import bvr.ObjectExistence;
import bvr.ObjectSubstitution;
import bvr.OpaqueVariationPoint;
import bvr.VPackageable;
import bvr.VSpec;
import bvr.VSpecResolution;
import bvr.VariationPoint;

public class CVLUtils {

	public static List<VariationPoint> getAllVariationPoints(VPackageable cvlModel) {
		ArrayList<VariationPoint> vps = new ArrayList<VariationPoint>();	
		TreeIterator<EObject> it = cvlModel.eAllContents();
		while (it.hasNext()) {
			EObject o = it.next();
			if (o instanceof VariationPoint) {
				vps.add((VariationPoint) o);
			}
		}
		return vps;
	}
	
	public static List<VSpecResolution> getResolutionForVSpec(VSpec vspec, VPackageable resolutionModel) {
		ArrayList<VSpecResolution> resolutions = new ArrayList<VSpecResolution>();	
		TreeIterator<EObject> it = resolutionModel.eAllContents();
		while (it.hasNext()) {
			VSpecResolution o = (VSpecResolution) it.next();
			if (o.getResolvedVSpec().getName().equals(vspec.getName())) {
				resolutions.add(o);
			}
		}
		return resolutions;
	}

	public static Boolean getVspecResolutionDecision(VSpecResolution res) {
		if (res instanceof ChoiceResolutuion) {
			return ((ChoiceResolutuion) res).isDecision();
		}
		return false;
	}

	/**
	 * 
	 * @param vp
	 * @return
	 */
	public static List<BaseModelHandle> getBaseModelHandleReferences(VariationPoint vp) {
		ArrayList<BaseModelHandle> refs = new ArrayList<BaseModelHandle>();	
		if (vp instanceof OpaqueVariationPoint) {
			refs.addAll(((OpaqueVariationPoint) vp).getPlaceHolder());
		} else if (vp instanceof ObjectExistence) {
			refs.add(((ObjectExistence) vp).getOptionalObject());
		} else if (vp instanceof LinkExistence) {
			refs.add(((LinkExistence) vp).getOptionalLink());
		} else if (vp instanceof ObjectSubstitution) {
			refs.add(((ObjectSubstitution) vp).getPlacementObject());
			refs.add(((ObjectSubstitution) vp).getReplacementObject());
		} // continue with all variation points.
		
		return refs;
	}
	
	
}
