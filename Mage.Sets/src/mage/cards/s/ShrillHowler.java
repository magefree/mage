package mage.cards.s;

import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesWithLessPowerEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.EldraziHorrorToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ShrillHowler extends TransformingDoubleFacedCard {

    public ShrillHowler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF, SubType.HORROR}, "{2}{G}",
                "Howling Chorus",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.WEREWOLF}, ""
        );

        // Shrill Howler
        this.getLeftHalfCard().setPT(3, 1);

        // Creatures with power less than Shrill Howler's power can't block it.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesWithLessPowerEffect()));

        // {5}{G}: Transform Shrill Howler.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{5}{G}")));

        // Howling Chorus
        this.getRightHalfCard().setPT(3, 5);

        // Creatures with power less than Howling Chorus's power can't block it.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesWithLessPowerEffect()));

        // Whenever Howling Chorus deals combat damage to a player, create a 3/2 colorless Eldrazi Horror creature token.
        this.getRightHalfCard().addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenEffect(new EldraziHorrorToken()), false));
    }

    private ShrillHowler(final ShrillHowler card) {
        super(card);
    }

    @Override
    public ShrillHowler copy() {
        return new ShrillHowler(this);
    }
}
