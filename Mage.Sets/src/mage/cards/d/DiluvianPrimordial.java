package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.targetadjustment.ForEachOpponentTargetsAdjuster;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DiluvianPrimordial extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant or sorcery card from that player's graveyard");

    public DiluvianPrimordial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Diluvian Primordial enters the battlefield, for each opponent, you may cast up to one target instant or sorcery card from that player's graveyard without paying its mana cost. If a card cast this way would be put into a graveyard this turn, exile it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiluvianPrimordialEffect(), false);
        ability.addTarget(new TargetCardInOpponentsGraveyard(0, 1, filter));
        ability.setTargetAdjuster(new ForEachOpponentTargetsAdjuster(true));
        this.addAbility(ability);
    }

    private DiluvianPrimordial(final DiluvianPrimordial card) {
        super(card);
    }

    @Override
    public DiluvianPrimordial copy() {
        return new DiluvianPrimordial(this);
    }
}

class DiluvianPrimordialEffect extends OneShotEffect {

    DiluvianPrimordialEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "for each opponent, you may cast up to one target "
                + "instant or sorcery card from that player's graveyard without "
                + "paying its mana cost. If a spell cast this way would be put "
                + "into a graveyard, exile it instead";
    }

    private DiluvianPrimordialEffect(final DiluvianPrimordialEffect effect) {
        super(effect);
    }

    @Override
    public DiluvianPrimordialEffect copy() {
        return new DiluvianPrimordialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Target target : source.getTargets()) {
                if (target instanceof TargetCardInOpponentsGraveyard) {
                    Card targetCard = game.getCard(target.getFirstTarget());
                    if (targetCard != null) {
                        new MayCastTargetCardEffect(CastManaAdjustment.WITHOUT_PAYING_MANA_COST, true)
                                .setTargetPointer(new FixedTarget(targetCard, game)).apply(game, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
