package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ControllerPlaysLandTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.constants.*;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.events.DrawCardEvent;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * @author freaisdead
 */
public final class ShanidSleepersScourge extends CardImpl {

    private static final FilterCreaturePermanent otherLegendaryCreaturesFilter = new FilterCreaturePermanent("other legendary creatures");
    private static final FilterSpell legendarySpellFilter = new FilterSpell("a legendary spell");
    private static final FilterPermanent legendaryLandFilter = new FilterPermanent("a legendary land");

    static {
        otherLegendaryCreaturesFilter.add(SuperType.LEGENDARY.getPredicate());

        legendarySpellFilter.add(SuperType.LEGENDARY.getPredicate());

        legendaryLandFilter.add(CardType.LAND.getPredicate());
        legendaryLandFilter.add(SuperType.LEGENDARY.getPredicate());
    }

    public ShanidSleepersScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Other legendary creatures you control have menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(false),
                Duration.WhileOnBattlefield,
                otherLegendaryCreaturesFilter,
                true)));
        // Whenever you play a legendary land or cast a legendary spell, you draw a card and you lose 1 life.
        Ability castLegendarySpellAbility = new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                legendarySpellFilter,
                false);
        castLegendarySpellAbility.addEffect(new LoseLifeSourceControllerEffect(1));
        this.addAbility(castLegendarySpellAbility);
        Ability playLegendaryLandAbility = new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1),
                legendaryLandFilter,
                false);
        playLegendaryLandAbility.addEffect(new LoseLifeSourceControllerEffect(1));
        this.addAbility(playLegendaryLandAbility);
    }

    private ShanidSleepersScourge(final ShanidSleepersScourge card) {
        super(card);
    }

    @Override
    public ShanidSleepersScourge copy() {
        return new ShanidSleepersScourge(this);
    }
}
