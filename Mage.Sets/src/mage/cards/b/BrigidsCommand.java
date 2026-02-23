package mage.cards.b;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.KithkinGreenWhiteToken;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrigidsCommand extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.KITHKIN);

    public BrigidsCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.SORCERY}, "{1}{G}{W}");

        this.subtype.add(SubType.KITHKIN);

        // Choose two --
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Create a token that's a copy of target Kithkin you control.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter).withChooseHint("to copy"));

        // * Target player creates a 1/1 green and white Kithkin creature token.
        this.getSpellAbility().addMode(new Mode(new CreateTokenTargetEffect(new KithkinGreenWhiteToken()))
                .addTarget(new TargetPlayer().withChooseHint("to create a token")));

        // * Target creature you control gets +3/+3 until end of turn.
        this.getSpellAbility().addMode(new Mode(new BoostTargetEffect(3, 3))
                .addTarget(new TargetControlledCreaturePermanent().withChooseHint("+3/+3")));

        // * Target creature you control fights target creature an opponent controls.
        this.getSpellAbility().addMode(new Mode(new FightTargetsEffect())
                .addTarget(new TargetControlledCreaturePermanent().withChooseHint("to fight"))
                .addTarget(new TargetOpponentsCreaturePermanent().withChooseHint("to fight")));
    }

    private BrigidsCommand(final BrigidsCommand card) {
        super(card);
    }

    @Override
    public BrigidsCommand copy() {
        return new BrigidsCommand(this);
    }
}
