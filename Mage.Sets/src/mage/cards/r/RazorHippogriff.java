package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class RazorHippogriff extends CardImpl {

    public RazorHippogriff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.HIPPOGRIFF);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD));
        ability.addEffect(new RazorHippogriffGainLifeEffect());

        this.addAbility(ability);
    }

    private RazorHippogriff(final RazorHippogriff card) {
        super(card);
    }

    @Override
    public RazorHippogriff copy() {
        return new RazorHippogriff(this);
    }
}

class RazorHippogriffGainLifeEffect extends OneShotEffect {

    public RazorHippogriffGainLifeEffect() {
        super(Outcome.GainLife);
        staticText = "you gain life equal to that card's mana value.";
    }

    private RazorHippogriffGainLifeEffect(final RazorHippogriffGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public RazorHippogriffGainLifeEffect copy() {
        return new RazorHippogriffGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player != null && card != null) {
            player.gainLife(card.getManaValue(), game, source);
            return true;
        }
        return false;
    }
}
