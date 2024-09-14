package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.LookAtOpponentFaceDownCreaturesAnyTimeEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FoundFootage extends CardImpl {

    public FoundFootage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.CLUE);

        // You may look at face-down creatures your opponents control any time.
        this.addAbility(new SimpleStaticAbility(
                new LookAtOpponentFaceDownCreaturesAnyTimeEffect(Duration.WhileOnBattlefield, TargetController.OPPONENT)
        ));

        // {2}, Sacrifice Found Footage: Surveil 2, then draw a card.
        Ability ability = new SimpleActivatedAbility(new SurveilEffect(2), new GenericManaCost(2));
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.addAbility(ability);
    }

    private FoundFootage(final FoundFootage card) {
        super(card);
    }

    @Override
    public FoundFootage copy() {
        return new FoundFootage(this);
    }
}
