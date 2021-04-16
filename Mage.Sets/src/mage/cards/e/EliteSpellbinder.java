package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class EliteSpellbinder extends CardImpl {

    public EliteSpellbinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Elite Spellbinder enters the battlefield, look at target opponent's hand. You may exile a nonland card from it. For as long as that card remains exiled, its owner may play it. A spell cast this way costs {2} more to cast.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EliteSpellbinderEffect()));
    }

    private EliteSpellbinder(final EliteSpellbinder card) {
        super(card);
    }

    @Override
    public EliteSpellbinder copy() {
        return new EliteSpellbinder(this);
    }
}

class EliteSpellbinderEffect extends OneShotEffect {

    EliteSpellbinderEffect() {
        super(Outcome.Benefit);
        staticText = "look at target opponent's hand. You may exile a nonland card from it. For as long as that card remains exiled, its owner may play it. A spell cast this way costs {2} more to cast";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        TargetOpponent targetOpponent = new TargetOpponent();

        controller.lookAtCards(source.getSourceObject(game).getName(), new CardsImpl());
        return false;
    }

    @Override
    public Effect copy() {
        return null;
    }
}