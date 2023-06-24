package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;

import java.util.UUID;

/**
 * @author freaisdead
 */
public final class ShanidSleepersScourge extends CardImpl {
    private static final FilterCreaturePermanent otherLegendaryCreaturesFilter = new FilterCreaturePermanent("legendary creatures");
    private static final FilterSpell legendarySpellFilter = new FilterSpell("a legendary spell");
    private static final FilterPermanent legendaryLandFilter = new FilterLandPermanent("a legendary land");

    static {
        otherLegendaryCreaturesFilter.add(SuperType.LEGENDARY.getPredicate());

        legendarySpellFilter.add(SuperType.LEGENDARY.getPredicate());

        legendaryLandFilter.add(SuperType.LEGENDARY.getPredicate());
    }

    public ShanidSleepersScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Other legendary creatures you control have menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(false),
                Duration.WhileOnBattlefield,
                otherLegendaryCreaturesFilter,
                true)));
        // Whenever you play a legendary land or cast a legendary spell, you draw a card and you lose 1 life.
        Ability ability = new OrTriggeredAbility(Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1).setText("you draw a card"), false,
                "Whenever you play a legendary land or cast a legendary spell, ",
                new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, null, legendaryLandFilter, true),
                new SpellCastControllerTriggeredAbility(null, legendarySpellFilter, false)
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private ShanidSleepersScourge(final ShanidSleepersScourge card) {
        super(card);
    }

    @Override
    public ShanidSleepersScourge copy() {
        return new ShanidSleepersScourge(this);
    }
}
