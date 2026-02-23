package mage.cards.a;

import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerOrBattleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AetherbladeAgent extends TransformingDoubleFacedCard {

    public AetherbladeAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ROGUE}, "{1}{B}",
                "Gitaxian Mindstinger",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.ROGUE}, "UB");
        this.getLeftHalfCard().setPT(1, 1);
        this.getRightHalfCard().setPT(3, 3);

        // Deathtouch
        this.getLeftHalfCard().addAbility(DeathtouchAbility.getInstance());

        // {4}{U/P}: Transform Aetherblade Agent. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{4}{U/P}")));

        // Gitaxian Mindstinger
        // Deathtouch
        this.getRightHalfCard().addAbility(DeathtouchAbility.getInstance());

        // Whenever Gitaxian Mindstinger deals combat damage to a player or battle, draw a card.
        this.getRightHalfCard().addAbility(new DealsCombatDamageToAPlayerOrBattleTriggeredAbility(new DrawCardSourceControllerEffect(1),false));
    }

    private AetherbladeAgent(final AetherbladeAgent card) {
        super(card);
    }

    @Override
    public AetherbladeAgent copy() {
        return new AetherbladeAgent(this);
    }
}
