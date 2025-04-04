package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 * @author balazskristof
 */
public final class EmetSelchUnsundered extends CardImpl {

    public EmetSelchUnsundered(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = mage.cards.h.HadesSorcererOfEld.class;

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Emet-Selch enters or attacks, draw a card, then discard a card.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DrawDiscardControllerEffect(1, 1)));

        // At the beginning of your upkeep, if there are fourteen or more cards in your graveyard, you may transform Emet-Selch.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new EmetSelchUnsunderedEffect()
        ));
        this.addAbility(new TransformAbility());
    }

    private EmetSelchUnsundered(final EmetSelchUnsundered card) {
        super(card);
    }

    @Override
    public EmetSelchUnsundered copy() {
        return new EmetSelchUnsundered(this);
    }
}

class EmetSelchUnsunderedEffect extends OneShotEffect {

    EmetSelchUnsunderedEffect() {
        super(Outcome.DrawCard);
        this.staticText = "if you have fourteen or more cards in your graveyard, you may transform {this}.";
    }

    private EmetSelchUnsunderedEffect(final EmetSelchUnsunderedEffect effect) {
        super(effect);
    }

    @Override
    public EmetSelchUnsunderedEffect copy() {
        return new EmetSelchUnsunderedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return controller != null
                && permanent != null
                && controller.getGraveyard().size() >= 14
                && controller.chooseUse(outcome, "Transform " + permanent.getName() + '?', source, game)
                && permanent.transform(source, game);
    }
}
