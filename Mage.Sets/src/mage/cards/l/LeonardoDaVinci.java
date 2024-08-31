package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author grimreap124
 */
public final class LeonardoDaVinci extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(SubType.THOPTER.getPredicate());
        filter.add(TargetController.YOU.getPlayerPredicate());
    }

    public LeonardoDaVinci(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        DynamicValue xValue = CardsInControllerHandCount.instance;
        // {3}{U}{U}: Until end of turn, Thopters you control have base power and toughness X/X, where X is the number of cards in your hand.
        this.addAbility(new SimpleActivatedAbility(new BoostControlledEffect(xValue, xValue, Duration.EndOfTurn, filter, false).setText(
                "Until end of turn, Thopters you control have base power and toughness X/X, where X is the number of cards in your hand."
        ), new ManaCostsImpl<>("{3}{U}{U}")));

        // {2}{U}, {T}: Draw a card, then discard a card. If the discarded card was an artifact card, exile it from your graveyard.
        // If you do, create a token that's a copy of it, except it's a 0/2 Thopter artifact creature with flying in addition to its other types.
        Ability ability = new SimpleActivatedAbility(new LeonardoDaVinciEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private LeonardoDaVinci(final LeonardoDaVinci card) {
        super(card);
    }

    @Override
    public LeonardoDaVinci copy() {
        return new LeonardoDaVinci(this);
    }
}

class LeonardoDaVinciEffect extends OneShotEffect {

    LeonardoDaVinciEffect() {
        super(Outcome.Benefit);
        staticText = "Draw a card, then discard a card. If the discarded card was an artifact card, exile it from your graveyard." +
                " If you do, create a token that's a copy of it, except it's a 0/2 Thopter artifact creature with flying in addition to its other types.";
    }

    protected LeonardoDaVinciEffect(final LeonardoDaVinciEffect effect) {
        super(effect);
    }

    @Override
    public LeonardoDaVinciEffect copy() {
        return new LeonardoDaVinciEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        player.drawCards(1, source, game);
        Card card = player.discardOne(false, false, source, game);

        if (card.isArtifact(game)) {
            player.moveCards(card, Zone.EXILED, source, game);
            // If you do, create a token that's a copy of it, except it's a 0/2 Thopter artifact creature with flying in addition to its other types.
            new CreateTokenCopyTargetEffect()
                    .setPermanentModifier((token) -> {
                        token.addCardType(CardType.CREATURE);
                        token.addSubType(SubType.THOPTER);
                        token.addCardType(CardType.ARTIFACT);
                        token.setPower(0);
                        token.setToughness(2);
                        token.addAbility(FlyingAbility.getInstance());
                    })
                    .setTargetPointer(new FixedTarget(card, game))
                    .apply(game, source);
        }

        return true;
    }
    
}