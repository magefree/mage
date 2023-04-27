package mage.cards.x;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class XystonStarDestroyer extends CardImpl {
    public XystonStarDestroyer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}{B}{B}");
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        //Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        //When Xyston Star Destroyer enters the battlefield, destroy target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public XystonStarDestroyer(final XystonStarDestroyer card) {
        super(card);
    }

    @Override
    public XystonStarDestroyer copy() {
        return new XystonStarDestroyer(this);
    }
}
