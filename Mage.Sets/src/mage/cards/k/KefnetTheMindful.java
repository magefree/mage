package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class KefnetTheMindful extends CardImpl {

    public KefnetTheMindful(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Kefnet the Mindful can't attack or block unless you have seven or more cards in your hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KefnetTheMindfulRestrictionEffect()));

        // {3}{U}: Draw a card, then you may return a land you control to its owner's hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new KefnetTheMindfulEffect(), new ManaCostsImpl<>("{3}{U}"));
        this.addAbility(ability);

    }

    private KefnetTheMindful(final KefnetTheMindful card) {
        super(card);
    }

    @Override
    public KefnetTheMindful copy() {
        return new KefnetTheMindful(this);
    }
}

class KefnetTheMindfulRestrictionEffect extends RestrictionEffect {

    public KefnetTheMindfulRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack or block unless you have seven or more cards in your hand";
    }

    public KefnetTheMindfulRestrictionEffect(final KefnetTheMindfulRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public KefnetTheMindfulRestrictionEffect copy() {
        return new KefnetTheMindfulRestrictionEffect(this);
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                return (controller.getHand().size() < 7);
            }
            return true;
        }  // do not apply to other creatures.
        return false;
    }
}

class KefnetTheMindfulEffect extends OneShotEffect {

    KefnetTheMindfulEffect() {
        super(Outcome.Benefit);
        staticText = "Draw a card, then you may return a land you control to its owner's hand";
    }

    KefnetTheMindfulEffect(final KefnetTheMindfulEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterControlledPermanent filterControlledLand = new FilterControlledPermanent("land you control");
        filterControlledLand.add(CardType.LAND.getPredicate());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(1, source, game);
            if (controller.chooseUse(Outcome.AIDontUseIt, "Return a land you control to its owner's hand?", source, game)) {
                Effect effect = new ReturnToHandChosenControlledPermanentEffect(filterControlledLand);
                effect.apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public KefnetTheMindfulEffect copy() {
        return new KefnetTheMindfulEffect(this);
    }
}
