
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class ElementalUprising extends CardImpl {

    public ElementalUprising(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Target land you control becomes a 4/4 Elemental creature with haste until end of turn. It's still a land. It must be blocked this turn if able.
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(new ElementalUprisingToken(), false, true, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetPermanent(new FilterControlledLandPermanent()));
        Effect effect = new MustBeBlockedByAtLeastOneTargetEffect(Duration.EndOfTurn);
        effect.setText("It must be blocked this turn if able");
        this.getSpellAbility().addEffect(effect);
    }

    private ElementalUprising(final ElementalUprising card) {
        super(card);
    }

    @Override
    public ElementalUprising copy() {
        return new ElementalUprising(this);
    }
}

class ElementalUprisingToken extends TokenImpl {

    public ElementalUprisingToken() {
        super("", "4/4 Elemental creature with haste");
        this.cardType.add(CardType.CREATURE);

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(HasteAbility.getInstance());
    }
    private ElementalUprisingToken(final ElementalUprisingToken token) {
        super(token);
    }

    public ElementalUprisingToken copy() {
        return new ElementalUprisingToken(this);
    }
}
