
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LoneFox
 */
public final class BelzenlokDemonToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();
    static {
        tokenImageSets.addAll(Arrays.asList("DOM"));
    }

    public BelzenlokDemonToken() {
        super("Demon", "6/6 black Demon creature token with flying, trample, and "
                + "\"At the beginning of your upkeep, sacrifice another creature.  If you can't, this creature deals 6 damage to you.\"");
        availableImageSetCodes = tokenImageSets;
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.DEMON);
        power = new MageInt(6);
        toughness = new MageInt(6);
        addAbility(FlyingAbility.getInstance());
        addAbility(TrampleAbility.getInstance());
        addAbility(new BeginningOfUpkeepTriggeredAbility(new BelzenlokDemonTokenEffect(), TargetController.YOU, false));
    }

    public BelzenlokDemonToken(final BelzenlokDemonToken token) {
        super(token);
    }

    @Override
    public BelzenlokDemonToken copy() {
        return new BelzenlokDemonToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode().equals("C14")) {
            this.setTokenType(2);
        }
    }
}

class BelzenlokDemonTokenEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another creature");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new AnotherPredicate());
    }

    BelzenlokDemonTokenEffect() {
        super(Outcome.Benefit);
        this.staticText = "sacrifice another creature.  If you can't, this creature deals 6 damage to you.";
    }

    BelzenlokDemonTokenEffect(final BelzenlokDemonTokenEffect effect) {
        super(effect);
    }

    @Override
    public BelzenlokDemonTokenEffect copy() {
        return new BelzenlokDemonTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int otherCreatures = new PermanentsOnBattlefieldCount(filter).calculate(game, source, this);
        if (otherCreatures > 0) {
            new SacrificeControllerEffect(filter, 1, "").apply(game, source);
        } else {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.damage(6, source.getSourceId(), game, false, true);
            }
        }
        return true;
    }
}
