package mage.cards.b;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAllCreatureTypesTargetEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BladesOfVelisVel extends CardImpl {

    public BladesOfVelisVel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.INSTANT}, "{1}{R}");
        this.subtype.add(SubType.SHAPESHIFTER);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Up to two target creatures each get +2/+0 and gain all creature types until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn)
                .setText("Up to two target creatures each get +2/+0"));
        this.getSpellAbility().addEffect(new GainAllCreatureTypesTargetEffect(Duration.EndOfTurn)
                .setText("and gain all creature types until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private BladesOfVelisVel(final BladesOfVelisVel card) {
        super(card);
    }

    @Override
    public BladesOfVelisVel copy() {
        return new BladesOfVelisVel(this);
    }
}
