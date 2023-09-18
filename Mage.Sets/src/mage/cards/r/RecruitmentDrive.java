package mage.cards.r;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SoldierToken;
import mage.game.permanent.token.WaylayToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RecruitmentDrive extends CardImpl {

    public RecruitmentDrive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(20);

        // 1-9 | Create two 1/1 white Soldier creature tokens.
        effect.addTableEntry(1, 9, new CreateTokenEffect(new SoldierToken(), 2));

        // 10-19 | Create two 2/2 white Knight creature tokens.
        effect.addTableEntry(10, 19, new CreateTokenEffect(new WaylayToken(), 2));

        // 20 | Create three 2/2 white Knight creature tokens.
        effect.addTableEntry(20, 20, new CreateTokenEffect(new WaylayToken(), 3));

        this.getSpellAbility().addEffect(effect);
    }

    private RecruitmentDrive(final RecruitmentDrive card) {
        super(card);
    }

    @Override
    public RecruitmentDrive copy() {
        return new RecruitmentDrive(this);
    }
}
