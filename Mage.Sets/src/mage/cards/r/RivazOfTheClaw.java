package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CastFromGraveyardOnceStaticAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.constants.*;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.card.CastFromZonePredicate;

/**
 *
 * @author weirddan455
 */
public final class RivazOfTheClaw extends CardImpl {

    private static final FilterCreatureSpell filter = new FilterCreatureSpell("Dragon creature spells");
    private static final FilterCreatureCard filter2 = new FilterCreatureCard();
    private static final FilterCreatureSpell filter3 = new FilterCreatureSpell("a Dragon creature spell from your graveyard");

    static {
        filter.add(SubType.DRAGON.getPredicate());
        filter2.add(SubType.DRAGON.getPredicate());
        filter3.add(SubType.DRAGON.getPredicate());
        filter3.add(new CastFromZonePredicate(Zone.GRAVEYARD));
    }

    public RivazOfTheClaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // {T}: Add two mana in any combination of colors. Spend this mana only to cast Dragon creature spells.
        this.addAbility(new ConditionalAnyColorManaAbility(2, new ConditionalSpellManaBuilder(filter)));

        // Once during each of your turns, you may cast a Dragon creature spell from your graveyard.
        this.addAbility(new CastFromGraveyardOnceStaticAbility(filter2, "Once during each of your turns, you may cast a Dragon creature spell from your graveyard"));

        // Whenever you cast a Dragon creature spell from your graveyard, it gains "When this creature dies, exile it."
        this.addAbility(new SpellCastControllerTriggeredAbility(
                Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(
                        new DiesSourceTriggeredAbility(new ExileSourceEffect().setText("exile it"), false),
                        Duration.Custom,
                        "it gains \"When this creature dies, exile it.\"",
                        true
                ),
                filter3, false, true, true
        ));
    }

    private RivazOfTheClaw(final RivazOfTheClaw card) {
        super(card);
    }

    @Override
    public RivazOfTheClaw copy() {
        return new RivazOfTheClaw(this);
    }
}
