package mage.cards.r;

import java.util.UUID;
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
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author nantuko
 */
public final class RazorHippogriff extends CardImpl {

    private static final FilterArtifactCard filter = new FilterArtifactCard("artifact card from your graveyard");

    public RazorHippogriff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.HIPPOGRIFF);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
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
