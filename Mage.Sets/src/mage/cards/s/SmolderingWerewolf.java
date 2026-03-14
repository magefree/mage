package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class SmolderingWerewolf extends TransformingDoubleFacedCard {

    public SmolderingWerewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF, SubType.HORROR}, "{2}{R}{R}",
                "Erupting Dreadwolf",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.WEREWOLF}, ""
        );

        // Smoldering Werewolf
        this.getLeftHalfCard().setPT(3, 2);

        // When Smoldering Werewolf enters the battlefield, it deals 1 damage to each of up to two target creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(1, "it"));
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.getLeftHalfCard().addAbility(ability);

        // {4}{R}{R}: Transform Smoldering Werewolf.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{4}{R}{R}")));

        // Erupting Dreadwolf
        this.getRightHalfCard().setPT(6, 4);

        // Whenever Erupting Dreadwolf attacks, it deals 2 damage to any target.
        Ability ability2 = new AttacksTriggeredAbility(new DamageTargetEffect(2, "it"), false);
        ability2.addTarget(new TargetAnyTarget());
        this.getRightHalfCard().addAbility(ability2);
    }

    private SmolderingWerewolf(final SmolderingWerewolf card) {
        super(card);
    }

    @Override
    public SmolderingWerewolf copy() {
        return new SmolderingWerewolf(this);
    }
}
