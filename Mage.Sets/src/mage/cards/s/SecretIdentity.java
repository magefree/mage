package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class SecretIdentity extends CardImpl {

    public SecretIdentity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");
        

        // Choose one --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        // * Conceal -- Until end of turn, target creature you control becomes a Citizen with base power and toughness 1/1 and gains hexproof.
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(1, 1, "Citizen with base power and toughness 1/1 and gains hexproof")
                        .withSubType(SubType.CITIZEN)
                        .withAbility(HexproofAbility.getInstance()),
                false, false, Duration.EndOfTurn)
        );
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().withFirstModeFlavorWord("Conceal");

        // * Reveal -- Until end of turn, target creature you control becomes a Hero with base power and toughness 3/4 and gains flying and vigilance.
        Mode mode = new Mode(new BecomesCreatureTargetEffect(
                new CreatureToken(3, 4, "Hero with base power and toughness 3/4 and gains flying and vigilance")
                        .withSubType(SubType.HERO)
                        .withAbility(FlyingAbility.getInstance())
                        .withAbility(VigilanceAbility.getInstance()),
                false, false, Duration.EndOfTurn));
        mode.withFlavorWord("Reveal");
        mode.addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private SecretIdentity(final SecretIdentity card) {
        super(card);
    }

    @Override
    public SecretIdentity copy() {
        return new SecretIdentity(this);
    }
}
