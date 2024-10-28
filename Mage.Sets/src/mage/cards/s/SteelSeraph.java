package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.PrototypeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SteelSeraph extends CardImpl {

    public SteelSeraph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Prototype {1}{W}{W} -- 3/3
        this.addAbility(new PrototypeAbility(this, "{1}{W}{W}", 3, 3));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of combat on your turn, target creature you control gains your choice of flying, vigilance, or lifelink until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new GainsChoiceOfAbilitiesEffect(
                        FlyingAbility.getInstance(), VigilanceAbility.getInstance(), LifelinkAbility.getInstance()),
                TargetController.YOU, false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private SteelSeraph(final SteelSeraph card) {
        super(card);
    }

    @Override
    public SteelSeraph copy() {
        return new SteelSeraph(this);
    }
}
