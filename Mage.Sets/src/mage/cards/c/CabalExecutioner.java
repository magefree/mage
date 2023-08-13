package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class CabalExecutioner extends CardImpl {

    public CabalExecutioner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Cabal Executioner deals combat damage to a player, that player sacrifices a creature.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_A_CREATURE, 1, "that player"), false, true));

        // Morph {3}{B}{B}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{3}{B}{B}")));
    }

    private CabalExecutioner(final CabalExecutioner card) {
        super(card);
    }

    @Override
    public CabalExecutioner copy() {
        return new CabalExecutioner(this);
    }
}
