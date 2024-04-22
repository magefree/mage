package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DalekDrone extends CardImpl {

    public DalekDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.DALEK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Exterminate! -- When Dalek Drone enters the battlefield, destroy target creature an opponent controls. That player loses 3 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addEffect(new LoseLifeTargetControllerEffect(3).setText("That player loses 3 life"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        ability.withFlavorWord("Exterminate!");
        this.addAbility(ability);
    }

    private DalekDrone(final DalekDrone card) {
        super(card);
    }

    @Override
    public DalekDrone copy() {
        return new DalekDrone(this);
    }
}
