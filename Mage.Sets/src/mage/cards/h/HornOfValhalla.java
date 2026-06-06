package mage.cards.h;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.EquipAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.permanent.token.SoldierToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HornOfValhalla extends AdventureCard {

    public HornOfValhalla(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.EQUIPMENT}, "{1}{W}",
                "Ysgard's Call",
                new CardType[]{CardType.SORCERY}, "{X}{W}{W}");

        // Equipped creature gets +1/+1 for each creature you control.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new BoostEquippedEffect(
                CreaturesYouControlCount.PLURAL, CreaturesYouControlCount.PLURAL
        ).setText("equipped creature gets +1/+1 for each creature you control")).addHint(CreaturesYouControlHint.instance));

        // Equip {3}
        this.getLeftHalfCard().addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), new TargetControlledCreaturePermanent(), false));

        // Ysgard's Call
        // Create X 1/1 white Soldier creature tokens.
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(
                new SoldierToken(), GetXValue.instance
        ));

        finalizeCard();
    }

    private HornOfValhalla(final HornOfValhalla card) {
        super(card);
    }

    @Override
    public HornOfValhalla copy() {
        return new HornOfValhalla(this);
    }
}
