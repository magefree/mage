package mage.cards.o;

import java.util.UUID;
import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TributeNotPaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TributeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class OracleOfBones extends CardImpl {

    public OracleOfBones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Tribute 2
        this.addAbility(new TributeAbility(2));
        // When Oracle of Bones enters the battlefield, if tribute wasn't paid, 
        // you may cast an instant or sorcery card from your hand without paying its mana cost.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new OracleOfBonesCastEffect(), false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, TributeNotPaidCondition.instance,
                "When {this} enters the battlefield, if its tribute wasn't paid, "
                + "you may cast an instant or sorcery card from your hand without paying its mana cost."));
    }

    private OracleOfBones(final OracleOfBones card) {
        super(card);
    }

    @Override
    public OracleOfBones copy() {
        return new OracleOfBones(this);
    }
}

class OracleOfBonesCastEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterInstantOrSorceryCard(
            "instant or sorcery card from your hand");

    public OracleOfBonesCastEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast an instant or sorcery card "
                + "from your hand without paying its mana cost";
    }

    public OracleOfBonesCastEffect(final OracleOfBonesCastEffect effect) {
        super(effect);
    }

    @Override
    public OracleOfBonesCastEffect copy() {
        return new OracleOfBonesCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCardInHand(filter);
            if (target.canChoose(source.getSourceId(), controller.getId(), game)
                    && controller.chooseUse(outcome, "Cast an instant or sorcery "
                            + "card from your hand without paying its mana cost?", source, game)) {
                Card cardToCast = null;
                boolean cancel = false;
                while (controller.canRespond() 
                        && !cancel) {
                    if (controller.chooseTarget(outcome, target, source, game)) {
                        cardToCast = game.getCard(target.getFirstTarget());
                        if (cardToCast != null
                                && cardToCast.getSpellAbility().canChooseTarget(game, controller.getId())) {
                            cancel = true;
                        }
                    } else {
                        cancel = true;
                    }
                }
                if (cardToCast != null) {
                    game.getState().setValue("PlayFromNotOwnHandZone" + cardToCast.getId(), Boolean.TRUE);
                    controller.cast(controller.chooseAbilityForCast(cardToCast, game, true),
                            game, true, new ApprovingObject(source, game));
                    game.getState().setValue("PlayFromNotOwnHandZone" + cardToCast.getId(), null);
                }
            }
            return true;
        }
        return false;
    }
}
