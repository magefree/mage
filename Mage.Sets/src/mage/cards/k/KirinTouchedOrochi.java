package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterNoncreatureCard;
import mage.game.Game;
import mage.game.permanent.token.SpiritToken;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class KirinTouchedOrochi extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card from a graveyard");
    private static final FilterNoncreatureCard filter2 = new FilterNoncreatureCard("noncreature card from a graveyard");

    public KirinTouchedOrochi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setGreen(true);
        this.nightCard = true;

        // Whenever Kirin-Touched Orochi attacks, choose one —
        // • Exile target creature card from a graveyard. When you do, create a 1/1 colorless Spirit creature token.
        Ability ability = new AttacksTriggeredAbility(new KirinTouchedOrochiTokenEffect());
        ability.addTarget(new TargetCardInGraveyard(filter));

        // • Exile target noncreature card from a graveyard. When you do, put a +1/+1 counter on target creature you control.
        Mode mode = new Mode(new KirinTouchedOrochiCounterEffect());
        mode.addTarget(new TargetCardInGraveyard(filter2));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private KirinTouchedOrochi(final KirinTouchedOrochi card) {
        super(card);
    }

    @Override
    public KirinTouchedOrochi copy() {
        return new KirinTouchedOrochi(this);
    }
}

class KirinTouchedOrochiTokenEffect extends OneShotEffect {

    public KirinTouchedOrochiTokenEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target creature card from a graveyard. When you do, create a 1/1 colorless Spirit creature token";
    }

    private KirinTouchedOrochiTokenEffect(final KirinTouchedOrochiTokenEffect effect) {
        super(effect);
    }

    @Override
    public KirinTouchedOrochiTokenEffect copy() {
        return new KirinTouchedOrochiTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        UUID targetId = source.getFirstTarget();
        Card card = game.getCard(targetId);
        if (controller == null || card == null || game.getState().getZone(targetId) != Zone.GRAVEYARD) {
            return false;
        }
        if (!controller.moveCards(card, Zone.EXILED, source, game)) {
            return false;
        }
        ReflexiveTriggeredAbility reflexiveTokenAbility = new ReflexiveTriggeredAbility(new CreateTokenEffect(new SpiritToken()), false);
        game.fireReflexiveTriggeredAbility(reflexiveTokenAbility, source);
        return true;
    }
}

class KirinTouchedOrochiCounterEffect extends OneShotEffect {

    public KirinTouchedOrochiCounterEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target noncreature card from a graveyard. When you do, put a +1/+1 counter on target creature you control";
    }

    private KirinTouchedOrochiCounterEffect(final KirinTouchedOrochiCounterEffect effect) {
        super(effect);
    }

    @Override
    public KirinTouchedOrochiCounterEffect copy() {
        return new KirinTouchedOrochiCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        UUID targetId = source.getFirstTarget();
        Card card = game.getCard(targetId);
        if (controller == null || card == null || game.getState().getZone(targetId) != Zone.GRAVEYARD) {
            return false;
        }
        if (!controller.moveCards(card, Zone.EXILED, source, game)) {
            return false;
        }
        ReflexiveTriggeredAbility reflexiveCounterAbility = new ReflexiveTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        reflexiveCounterAbility.addTarget(new TargetControlledCreaturePermanent());
        game.fireReflexiveTriggeredAbility(reflexiveCounterAbility, source);
        return true;
    }
}
