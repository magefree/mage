package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.util.CardUtil;

/**
 *
 * @author DominionSpy
 */
public final class GoblinMaskmaker extends CardImpl {

    private static final FilterCard filter = new FilterCard("face-down spells");

    static {
        filter.add(FaceDownPredicate.instance);
    }

    public GoblinMaskmaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Goblin Maskmaker attacks, face-down spells you cast this turn cost {1} less to cast.
        this.addAbility(new AttacksTriggeredAbility(new GoblinMaskmakerEffect()));
    }

    private GoblinMaskmaker(final GoblinMaskmaker card) {
        super(card);
    }

    @Override
    public GoblinMaskmaker copy() {
        return new GoblinMaskmaker(this);
    }
}

class GoblinMaskmakerEffect extends CostModificationEffectImpl {

    GoblinMaskmakerEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.staticText = "face-down spells you cast this turn cost {1} less to cast";
    }

    private GoblinMaskmakerEffect(final GoblinMaskmakerEffect effect) {
        super(effect);
    }

    @Override
    public GoblinMaskmakerEffect copy() {
        return new GoblinMaskmakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!abilityToModify.isControlledBy(source.getControllerId())) {
            return false;
        }
        if (abilityToModify instanceof SpellAbility) {
            Spell spell = game.getSpell(abilityToModify.getId());
            if (spell == null) {
                return false;
            }

            return spell.isFaceDown(game);
        }
        return false;
    }
}
