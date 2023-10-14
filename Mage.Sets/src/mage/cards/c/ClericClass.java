package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BecomesClassLevelTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.replacement.GainPlusOneLifeReplacementEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClericClass extends CardImpl {

    public ClericClass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // If you would gain life, you gain that much life plus 1 instead.
        this.addAbility(new SimpleStaticAbility(new GainPlusOneLifeReplacementEffect()));

        // {3}{W}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{3}{W}"));

        // Whenever you gain life, put a +1/+1 counter on target creature you control.
        Ability ability = new GainLifeControllerTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 2)));

        // {4}{W}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{4}{W}"));

        // When this Class becomes level 3, return target creature card from your graveyard to the battlefield. You gain life equal to its toughness.
        ability = new BecomesClassLevelTriggeredAbility(new ClericClassReturnEffect(), 3);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private ClericClass(final ClericClass card) {
        super(card);
    }

    @Override
    public ClericClass copy() {
        return new ClericClass(this);
    }
}

class ClericClassReturnEffect extends OneShotEffect {

    ClericClassReturnEffect() {
        super(Outcome.Benefit);
        staticText = "return target creature card from your graveyard to the battlefield. " +
                "You gain life equal to its toughness";
    }

    private ClericClassReturnEffect(final ClericClassReturnEffect effect) {
        super(effect);
    }

    @Override
    public ClericClassReturnEffect copy() {
        return new ClericClassReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        int toughness = permanent != null ? permanent.getToughness().getValue() : card.getToughness().getValue();
        player.gainLife(toughness, game, source);
        return true;
    }
}
