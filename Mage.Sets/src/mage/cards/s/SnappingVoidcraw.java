package mage.cards.s;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SnappingVoidcraw extends CardImpl {

    public SnappingVoidcraw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // {T}: Add {C}{C}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost()));

        // {3}{C}, {T}: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new ManaCostsImpl<>("{3}{C}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private SnappingVoidcraw(final SnappingVoidcraw card) {
        super(card);
    }

    @Override
    public SnappingVoidcraw copy() {
        return new SnappingVoidcraw(this);
    }
}
