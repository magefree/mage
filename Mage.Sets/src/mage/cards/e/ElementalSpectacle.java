package mage.cards.e;

import java.util.UUID;

import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.game.permanent.token.OmnathElementalToken;

/**
 *
 * @author muz
 */
public final class ElementalSpectacle extends CardImpl {

    public ElementalSpectacle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}");

        // Vivid -- Create a number of 5/5 red and green Elemental creature tokens equal to the number of colors among permanents you control. Then you gain life equal to the number of creatures you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
            new OmnathElementalToken(),
            ColorsAmongControlledPermanentsCount.ALL_PERMANENTS
        ));
        this.getSpellAbility().addEffect(new GainLifeEffect(CreaturesYouControlCount.PLURAL)
            .setText(", then you gain life equal to the number of creatures you control"));
        this.getSpellAbility().setAbilityWord(AbilityWord.VIVID);
        this.getSpellAbility().addHint(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getHint());
    }

    private ElementalSpectacle(final ElementalSpectacle card) {
        super(card);
    }

    @Override
    public ElementalSpectacle copy() {
        return new ElementalSpectacle(this);
    }
}
