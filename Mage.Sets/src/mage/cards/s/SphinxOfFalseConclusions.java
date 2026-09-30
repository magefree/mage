package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.abilities.condition.Condition;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class SphinxOfFalseConclusions extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("it isn't a token");
    static { filter.add(TokenPredicate.FALSE); }
    private static final Condition condition = new SourceMatchesFilterCondition(filter);

    public SphinxOfFalseConclusions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature attacks, draw a card, then discard a card.
        this.addAbility(new AttacksTriggeredAbility(new DrawDiscardControllerEffect(1, 1)));

        // When this creature dies, if it isn't a token, create a token that's a copy of it.
        this.addAbility(new DiesSourceTriggeredAbility(new SphinxOfFalseConclusionsEffect(), false, SetTargetPointer.CARD).withInterveningIf(condition));
    }

    private SphinxOfFalseConclusions(final SphinxOfFalseConclusions card) {
        super(card);
    }

    @Override
    public SphinxOfFalseConclusions copy() {
        return new SphinxOfFalseConclusions(this);
    }
}

class SphinxOfFalseConclusionsEffect extends OneShotEffect {

    SphinxOfFalseConclusionsEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of it";
    }

    private SphinxOfFalseConclusionsEffect(final SphinxOfFalseConclusionsEffect effect) {
        super(effect);
    }

    @Override
    public SphinxOfFalseConclusionsEffect copy() {
        return new SphinxOfFalseConclusionsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent copyFrom = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if(controller == null || copyFrom == null) {
            return false;
        }

        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(true);
        effect.setTargetPointer(new FixedTarget(copyFrom.getId(),game));
        effect.apply(game, source);

        return true;
    }
}
