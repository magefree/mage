package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.ElementalTokenWithHaste;

import java.util.UUID;

/**
 * @author Loki
 */
public final class RakkaMar extends CardImpl {

    public RakkaMar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(HasteAbility.getInstance());

        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new ElementalTokenWithHaste()),
                new ColoredManaCost(ColoredManaSymbol.R)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private RakkaMar(final RakkaMar card) {
        super(card);
    }

    @Override
    public RakkaMar copy() {
        return new RakkaMar(this);
    }

}
