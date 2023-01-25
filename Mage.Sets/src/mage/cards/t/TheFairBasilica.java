package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class TheFairBasilica extends CardImpl {

    public TheFairBasilica(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.SPHERE);

        // The Fair Basilica enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {1}{W}, {T}, Sacrifice The Fair Basilica: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private TheFairBasilica(final TheFairBasilica card) {
        super(card);
    }

    @Override
    public TheFairBasilica copy() {
        return new TheFairBasilica(this);
    }
}
