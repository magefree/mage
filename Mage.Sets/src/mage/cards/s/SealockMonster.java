package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SealockMonster extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent(SubType.ISLAND, "an Island");

    public SealockMonster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.OCTOPUS);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Sealock Monster can't attack unless defending player controls an Island.
        this.addAbility(new SimpleStaticAbility(new CantAttackUnlessDefenderControllsPermanent(filter)));

        // {5}{U}{U}: Monstrosity 3.</i>
        this.addAbility(new MonstrosityAbility("{5}{U}{U}", 3));

        // When Sealock Monster becomes monstrous, target land becomes an island in addition to its other types.
        Ability ability = new BecomesMonstrousSourceTriggeredAbility(
                new AddCardSubTypeTargetEffect(SubType.ISLAND, Duration.Custom)
        );
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private SealockMonster(final SealockMonster card) {
        super(card);
    }

    @Override
    public SealockMonster copy() {
        return new SealockMonster(this);
    }
}
