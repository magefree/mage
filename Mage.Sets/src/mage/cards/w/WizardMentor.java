package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class WizardMentor extends CardImpl {

    public WizardMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Return Wizard Mentor and target creature you control to their owner's hand.
        Ability ability = new SimpleActivatedAbility(new WizardMentorEffect(), new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private WizardMentor(final WizardMentor card) {
        super(card);
    }

    @Override
    public WizardMentor copy() {
        return new WizardMentor(this);
    }
}

class WizardMentorEffect extends OneShotEffect {

    WizardMentorEffect() {
        super(Outcome.Benefit);
        staticText = "return {this} and target creature you control to their owner's hand";
    }

    private WizardMentorEffect(final WizardMentorEffect effect) {
        super(effect);
    }

    @Override
    public WizardMentorEffect copy() {
        return new WizardMentorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(source.getSourcePermanentIfItStillExists(game));
        cards.add(source.getFirstTarget());
        return player.moveCards(cards, Zone.HAND, source, game);
    }
}
