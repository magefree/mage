package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LokhustHeavyDestroyer extends CardImpl {

    public LokhustHeavyDestroyer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}{B}{B}");

        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Enmitic Exterminator -- When Lokhust Heavy Destroyer enters the battlefield, each plater sacrifices a creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeAllEffect(
                1, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
        )).withFlavorWord("Enmitic Exterminator"));

        // Unearth {5}{B}{B}{B}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{5}{B}{B}{B}")));
    }

    private LokhustHeavyDestroyer(final LokhustHeavyDestroyer card) {
        super(card);
    }

    @Override
    public LokhustHeavyDestroyer copy() {
        return new LokhustHeavyDestroyer(this);
    }
}
