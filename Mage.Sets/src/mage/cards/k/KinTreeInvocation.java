
package mage.cards.k;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public final class KinTreeInvocation extends CardImpl {

    public KinTreeInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{G}");

        // Create an X/X black and green Spirit Warrior creature token, where X is the greatest toughness among creatures you control.
        this.getSpellAbility().addEffect(new KinTreeInvocationCreateTokenEffect());

    }

    private KinTreeInvocation(final KinTreeInvocation card) {
        super(card);
    }

    @Override
    public KinTreeInvocation copy() {
        return new KinTreeInvocation(this);
    }
}

class KinTreeInvocationCreateTokenEffect extends OneShotEffect {

    public KinTreeInvocationCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create an X/X black and green Spirit Warrior creature token, where X is the greatest toughness among creatures you control";
    }

    public KinTreeInvocationCreateTokenEffect(final KinTreeInvocationCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public KinTreeInvocationCreateTokenEffect copy() {
        return new KinTreeInvocationCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int value = Integer.MIN_VALUE;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)) {
            if (value < permanent.getToughness().getValue()) {
                value = permanent.getToughness().getValue();
            }
        }
        Token token = new SpiritWarriorToken(value);
        token.getAbilities().newId(); // neccessary if token has ability like DevourAbility()
        token.putOntoBattlefield(1, game, source, source.getControllerId());
        return true;
    }

}

class SpiritWarriorToken extends TokenImpl {

    public SpiritWarriorToken() {
        this(1);
    }

    public SpiritWarriorToken(int x) {
        super("Spirit Warrior Token", "X/X black and green Spirit Warrior creature token, where X is the greatest toughness among creatures you control");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WARRIOR);
        this.color.setBlack(true);
        this.color.setGreen(true);
        this.power = new MageInt(x);
        this.toughness = new MageInt(x);
    }

    public SpiritWarriorToken(final SpiritWarriorToken token) {
        super(token);
    }

    public SpiritWarriorToken copy() {
        return new SpiritWarriorToken(this);
    }
}
