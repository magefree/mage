package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.AttacksPlayerWithCreaturesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SquallGunbladeDuelist extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(SquallGunbladeDuelistPredicate.instance);
    }

    public SquallGunbladeDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // As Squall enters, choose a number.
        this.addAbility(new AsEntersBattlefieldAbility(new SquallGunbladeDuelistChooseEffect()));

        // Whenever one or more creatures attack one of your opponents, if any of those creatures have power or toughness equal to the chosen number, Squall deals damage equal to its power to defending player.
        this.addAbility(new AttacksPlayerWithCreaturesTriggeredAbility(
                new SquallGunbladeDuelistDamageEffect(), 1,
                filter, SetTargetPointer.PLAYER, true
        ).setTriggerPhrase("Whenever one or more creatures attack one of your opponents, " +
                "if any of those creatures have power or toughness equal to the chosen number, "));
    }

    private SquallGunbladeDuelist(final SquallGunbladeDuelist card) {
        super(card);
    }

    @Override
    public SquallGunbladeDuelist copy() {
        return new SquallGunbladeDuelist(this);
    }
}

enum SquallGunbladeDuelistPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Integer number = (Integer) game
                .getState()
                .getValue(CardUtil.getObjectZoneString(
                        "chosenNumber", input.getSource().getId(), game,
                        input.getSource().getStackMomentSourceZCC(), true
                ));
        return number != null
                && (input.getObject().getPower().getValue() == number
                || input.getObject().getToughness().getValue() == number);
    }
}

class SquallGunbladeDuelistChooseEffect extends OneShotEffect {

    SquallGunbladeDuelistChooseEffect() {
        super(Outcome.Benefit);
        staticText = "choose a number";
    }

    private SquallGunbladeDuelistChooseEffect(final SquallGunbladeDuelistChooseEffect effect) {
        super(effect);
    }

    @Override
    public SquallGunbladeDuelistChooseEffect copy() {
        return new SquallGunbladeDuelistChooseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int number = player.getAmount(0, Integer.MAX_VALUE, "Choose a number", source, game);
        game.getState().setValue(CardUtil.getObjectZoneString(
                "chosenNumber", source.getId(), game,
                source.getStackMomentSourceZCC(), false
        ), number);
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent != null) {
            permanent.addInfo("chosen number", "<font color = 'blue'>Chosen Number: " + number + "</font>", game);
            game.informPlayers(permanent.getLogName() + ", chosen number: " + number);
        }
        return true;
    }
}

class SquallGunbladeDuelistDamageEffect extends OneShotEffect {

    SquallGunbladeDuelistDamageEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals damage equal to its power to defending player";
    }

    private SquallGunbladeDuelistDamageEffect(final SquallGunbladeDuelistDamageEffect effect) {
        super(effect);
    }

    @Override
    public SquallGunbladeDuelistDamageEffect copy() {
        return new SquallGunbladeDuelistDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        return player != null
                && permanent != null
                && player.damage(permanent.getPower().getValue(), permanent.getId(), source, game) > 0;
    }
}
