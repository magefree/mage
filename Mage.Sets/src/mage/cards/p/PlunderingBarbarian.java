package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlunderingBarbarian extends CardImpl {

    public PlunderingBarbarian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Plundering Barbarian enters the battlefield, choose one —
        // • Smash the Chest — Destroy target artifact.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetArtifactPermanent());
        ability.getModes().getMode().withFlavorWord("Smash the Chest");

        // • Pry It Open — Creature a Treasure token.
        ability.addMode(new Mode(
                new CreateTokenEffect(new TreasureToken())
        ).withFlavorWord("Pry It Open"));
        this.addAbility(ability);
    }

    private PlunderingBarbarian(final PlunderingBarbarian card) {
        super(card);
    }

    @Override
    public PlunderingBarbarian copy() {
        return new PlunderingBarbarian(this);
    }
}
