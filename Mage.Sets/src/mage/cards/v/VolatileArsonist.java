package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VolatileArsonist extends TransformingDoubleFacedCard {

    public VolatileArsonist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{3}{R}{R}",
                "Dire-Strain Anarchist",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Volatile Arsonist
        this.getLeftHalfCard().setPT(4, 4);

        // Menace
        this.getLeftHalfCard().addAbility(new MenaceAbility(false));

        // Haste
        this.getLeftHalfCard().addAbility(HasteAbility.getInstance());

        // Whenever Volatile Arsonist attacks, it deals 1 damage to each of up to one target creature, up to one target player, and/or up to one target planeswalker.
        Ability ability = new AttacksTriggeredAbility(new DamageTargetEffect(1)
                .setText("it deals 1 damage to each of up to one target creature, up to one target player, and/or up to one target planeswalker"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.addTarget(new TargetPlayer(0, 1, false));
        ability.addTarget(new TargetPlaneswalkerPermanent(0, 1));
        this.getLeftHalfCard().addAbility(ability);

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Dire-Strain Anarchist
        this.getRightHalfCard().setPT(5, 5);

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility(false));

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());

        // Whenever Dire-Strain Anarchist attacks, it deals 2 damage to each of up to one target creature, up to one target player, and/or up to one target planeswalker.
        Ability ability2 = new AttacksTriggeredAbility(new DamageTargetEffect(2).setText("it deals 2 damage to each of up to one target creature, up to one target player, and/or up to one target planeswalker"));
        ability2.addTarget(new TargetCreaturePermanent(0, 1));
        ability2.addTarget(new TargetPlayer(0, 1, false));
        ability2.addTarget(new TargetPlaneswalkerPermanent(0, 1));
        this.getRightHalfCard().addAbility(ability2);

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private VolatileArsonist(final VolatileArsonist card) {
        super(card);
    }

    @Override
    public VolatileArsonist copy() {
        return new VolatileArsonist(this);
    }
}
