package mage.cards.t;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TangleclawWerewolf extends TransformingDoubleFacedCard {

    public TangleclawWerewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF, SubType.HORROR}, "{2}{G}{G}",
                "Fibrous Entangler",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.WEREWOLF}, ""
        );

        // Tangleclaw Werewolf
        this.getLeftHalfCard().setPT(2, 4);

        // Tangleclaw Werewolf can block an additional creature each combat.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new CanBlockAdditionalCreatureEffect(Duration.WhileOnBattlefield, 1)));

        // {6}{G}: Transform Tangleclaw Werewolf.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{6}{G}")));

        // Fibrous Entangler
        this.getRightHalfCard().setPT(4, 6);

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // Fibrous Entangler must be blocked if able.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new MustBeBlockedByAtLeastOneSourceEffect(Duration.WhileOnBattlefield)));

        // Fibrous Entangler can block an additional creature each combat.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new CanBlockAdditionalCreatureEffect(Duration.WhileOnBattlefield, 1)));
    }

    private TangleclawWerewolf(final TangleclawWerewolf card) {
        super(card);
    }

    @Override
    public TangleclawWerewolf copy() {
        return new TangleclawWerewolf(this);
    }
}
