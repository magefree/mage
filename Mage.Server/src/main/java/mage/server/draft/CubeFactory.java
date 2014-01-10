/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/
package mage.server.draft;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import mage.game.draft.DraftCube;
import org.apache.log4j.Logger;

/**
 *
 * @author LevelX2
 */
public class CubeFactory {

    private static final CubeFactory INSTANCE = new CubeFactory();
    private static final Logger logger = Logger.getLogger(CubeFactory.class);

    private Map<String, Class> draftCubes = new LinkedHashMap<String, Class>();

    public static CubeFactory getInstance() {
        return INSTANCE;
    }

    private CubeFactory() {}

    public DraftCube createDraftCube(String draftCubeName) {

        DraftCube draftCube;
        Constructor<?> con;
        try {
            con = draftCubes.get(draftCubeName).getConstructor(new Class[]{});
            draftCube = (DraftCube)con.newInstance(new Object[] {});
        } catch (Exception ex) {
            logger.fatal("CubeFactory error", ex);
            return null;
        }
        logger.debug("Draft cube created: " + draftCube.getName());

        return draftCube;
    }

    public Set<String> getDraftCubes() {
        return draftCubes.keySet();
    }

    public void addDraftCube(String name, Class draftCube) {
        if (draftCube != null) {
            this.draftCubes.put(name, draftCube);
        }
    }

}
