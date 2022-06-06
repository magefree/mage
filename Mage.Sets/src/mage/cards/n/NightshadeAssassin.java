
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class NightshadeAssassin extends CardImpl {

    public NightshadeAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When Nightshade Assassin enters the battlefield, you may reveal X black cards in your hand. If you do, target creature gets -X/-X until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new NightshadeAssassinEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Madness {1}{B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{1}{B}")));
    }

    private NightshadeAssassin(final NightshadeAssassin card) {
        super(card);
    }

    @Override
    public NightshadeAssassin copy() {
        return new NightshadeAssassin(this);
    }
}

class NightshadeAssassinEffect extends OneShotEffect {

    public NightshadeAssassinEffect() {
        super(Outcome.UnboostCreature);
        staticText = "you may reveal X black cards in your hand. If you do, target creature gets -X/-X until end of turn";
    }

    public NightshadeAssassinEffect(final NightshadeAssassinEffect effect) {
        super(effect);
    }

    @Override
    public NightshadeAssassinEffect copy() {
        return new NightshadeAssassinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        FilterCard filter = new FilterCard();
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        int blackCards = controller.getHand().count(filter, source.getControllerId(), source, game);
        int cardsToReveal = controller.getAmount(0, blackCards, "Reveal how many black cards?", game);
        game.informPlayers(controller.getLogName() + " chooses to reveal " + cardsToReveal + " black cards.");
        if (cardsToReveal > 0) {
            TargetCardInHand target = new TargetCardInHand(cardsToReveal, cardsToReveal, filter);
            if (controller.choose(Outcome.Benefit, target, source, game)) {
                controller.revealCards(sourceObject.getIdName(), new CardsImpl(target.getTargets()), game);
                int unboost = target.getTargets().size() * -1;
                ContinuousEffect effect = new BoostTargetEffect(unboost, unboost, Duration.EndOfTurn);
                effect.setTargetPointer(getTargetPointer());
                game.addEffect(effect, source);
            }
        }
        return true;
    }
}
