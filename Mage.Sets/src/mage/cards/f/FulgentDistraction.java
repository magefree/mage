

package mage.cards.f;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class FulgentDistraction extends CardImpl {

    public FulgentDistraction (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        this.getSpellAbility().addEffect(new FulgentDistractionEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));
    }

    private FulgentDistraction(final FulgentDistraction card) {
        super(card);
    }

    @Override
    public FulgentDistraction copy() {
        return new FulgentDistraction(this);
    }
}

class FulgentDistractionEffect extends OneShotEffect {

    private static String text = "Choose two target creatures. Tap those creatures, then unattach all Equipment from them";

    FulgentDistractionEffect ( ) {
        super(Outcome.Tap);
        staticText = text;
    }

    FulgentDistractionEffect ( FulgentDistractionEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for ( UUID target : targetPointer.getTargets(game, source) ) {
            Permanent creature = game.getPermanent(target);

            List<UUID> copiedAttachments = new ArrayList<>(creature.getAttachments());
            for ( UUID equipmentId : copiedAttachments ) {
                Permanent equipment = game.getPermanent(equipmentId);
                boolean isEquipment = false;

                for ( Ability ability : equipment.getAbilities() ) {
                    if ( ability instanceof EquipAbility ) {
                        isEquipment = true;
                    }
                }

                if ( isEquipment ) {
                    creature.removeAttachment(equipmentId, source, game);
                }
            }

            creature.tap(source, game);
        }
        return true;
    }

    @Override
    public FulgentDistractionEffect copy() {
        return new FulgentDistractionEffect(this);
    }

}
