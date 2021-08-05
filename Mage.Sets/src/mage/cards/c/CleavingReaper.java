package mage.cards.c;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

/**
 *
 * @author weirddan455
 */
public final class CleavingReaper extends CardImpl {

    private static final Hint hint = new ConditionHint(CleavingReaperCondition.instance, "Can return from graveyard");

    public CleavingReaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Pay 3 life: Return Cleaving Reaper from your graveyard to your hand.
        // Activate this ability only if you had an Angel or Berserker enter the battlefield under your control this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new PayLifeCost(3), CleavingReaperCondition.instance
        );
        ability.addHint(hint);
        this.addAbility(ability, new PermanentsEnteredBattlefieldWatcher());
    }

    private CleavingReaper(final CleavingReaper card) {
        super(card);
    }

    @Override
    public CleavingReaper copy() {
        return new CleavingReaper(this);
    }
}

enum CleavingReaperCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PermanentsEnteredBattlefieldWatcher watcher = game.getState().getWatcher(PermanentsEnteredBattlefieldWatcher.class);
        if (watcher != null) {
            List<Permanent> permanents = watcher.getThisTurnEnteringPermanents(source.getControllerId());
            if (permanents != null) {
                for (Permanent permanent : permanents) {
                    if (permanent.hasSubtype(SubType.ANGEL, game) || permanent.hasSubtype(SubType.BERSERKER, game)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "if you had an Angel or Berserker enter the battlefield under your control this turn";
    }
}
