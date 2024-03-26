package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.game.Game;

/**
 *
 * @author DominionSpy
 */
public final class GoblinMaskmaker extends CardImpl {

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

class GoblinMaskmakerEffect extends SpellsCostReductionControllerEffect {

    private static final FilterCard filter = new FilterCard("face-down spells");

    GoblinMaskmakerEffect() {
        super(filter, 1);
        this.duration = Duration.EndOfTurn;
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
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            SpellAbility spellAbility = (SpellAbility) abilityToModify;
            if (spellAbility.getSpellAbilityCastMode().isFaceDown()) {
                return super.applies(abilityToModify, source, game);
            }
        }
        return false;
    }
}
