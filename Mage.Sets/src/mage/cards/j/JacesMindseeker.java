package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class JacesMindseeker extends CardImpl {

    public JacesMindseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Jace's Mindseeker enters the battlefield, target opponent puts 
        // the top five cards of their library into their graveyard.
        // You may cast an instant or sorcery card from among them without paying its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new JaceMindseekerEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private JacesMindseeker(final JacesMindseeker card) {
        super(card);
    }

    @Override
    public JacesMindseeker copy() {
        return new JacesMindseeker(this);
    }
}

class JaceMindseekerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterInstantOrSorceryCard();

    public JaceMindseekerEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "target opponent mills five cards. You may cast an instant or sorcery spell " +
                "from among them without paying its mana cost.";
    }

    public JaceMindseekerEffect(final JaceMindseekerEffect effect) {
        super(effect);
    }

    @Override
    public JaceMindseekerEffect copy() {
        return new JaceMindseekerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null) {
            return false;
        }
        return CardUtil.castSpellWithAttributesForFree(
                controller, source, game,
                opponent.millCards(5, source, game),
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY
        );
    }
}
