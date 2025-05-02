package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrTurnedFaceUpTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CulvertAmbusher extends CardImpl {

    public CulvertAmbusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.WURM);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Culvert Ambusher enters the battlefield or is turned face up, target creature blocks this turn if able.
        Ability ability = new EntersBattlefieldOrTurnedFaceUpTriggeredAbility(new BlocksIfAbleTargetEffect(Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Disguise {4}{G}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{4}{G}")));
    }

    private CulvertAmbusher(final CulvertAmbusher card) {
        super(card);
    }

    @Override
    public CulvertAmbusher copy() {
        return new CulvertAmbusher(this);
    }
}
