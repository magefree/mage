package mage.cards.t;

import mage.abilities.common.MayCastFromGraveyardSourceAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.NecronWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheirNumberIsLegion extends CardImpl {

    public TheirNumberIsLegion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}{B}{B}");

        // Create X tapped 2/2 black Necron Warrior artifact creature tokens, then you gain life equal to the number of artifacts you control. Exile Their Number Is Legion.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new NecronWarriorToken(), ManacostVariableValue.REGULAR, true, false
        ));
        this.getSpellAbility().addEffect(new GainLifeEffect(ArtifactYouControlCount.instance)
                .setText(", then you gain life equal to the number of artifacts you control"));
        this.getSpellAbility().addEffect(new ExileSpellEffect());

        // You may cast Their Number Is Legion from your graveyard.
        this.addAbility(new MayCastFromGraveyardSourceAbility());
    }

    private TheirNumberIsLegion(final TheirNumberIsLegion card) {
        super(card);
    }

    @Override
    public TheirNumberIsLegion copy() {
        return new TheirNumberIsLegion(this);
    }
}
