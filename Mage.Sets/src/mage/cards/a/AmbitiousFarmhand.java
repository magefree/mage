package mage.cards.a;

import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AmbitiousFarmhand extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCard("basic Plains card");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(SubType.PLAINS.getPredicate());
    }

    public AmbitiousFarmhand(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.PEASANT}, "{1}{W}",
                "Seasoned Cathar",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.KNIGHT}, "W"
        );
        this.getLeftHalfCard().setPT(1, 1);
        this.getRightHalfCard().setPT(3, 3);

        // When Ambitious Farmhand enters the battlefield, you may search your library for a basic Plains card, reveal it, put it into your hand, then shuffle.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), true
        ));

        // Coven—{1}{W}{W}: Transform Ambitious Farmhand. Activate only if you control three or more creatures with different powers.
        this.getLeftHalfCard().addAbility(new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new TransformSourceEffect(),
                new ManaCostsImpl<>("{1}{W}{W}"), CovenCondition.instance
        ).setAbilityWord(AbilityWord.COVEN).addHint(CovenHint.instance));

        // Seasoned Cathar
        // Lifelink
        this.getRightHalfCard().addAbility(LifelinkAbility.getInstance());
    }

    private AmbitiousFarmhand(final AmbitiousFarmhand card) {
        super(card);
    }

    @Override
    public AmbitiousFarmhand copy() {
        return new AmbitiousFarmhand(this);
    }
}
