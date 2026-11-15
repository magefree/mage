package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.game.permanent.token.CadetToken;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CraftworkCrusher extends CardImpl {

    public CraftworkCrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{R}{R}{G}{G}");

        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When this creature enters, choose two --
        // * This creature deals 4 damage to target creature or planeswalker.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(4));
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);
        ability.getModes().setMinModes(2);
        ability.getModes().setMaxModes(2);

        // * Create a 2/2 colorless Wizard Soldier creature token named Cadet.
        ability.addMode(new Mode(new CreateTokenEffect(new CadetToken())));

        // * Draw a card.
        ability.addMode(new Mode(new DrawCardSourceControllerEffect(1)));
    }

    private CraftworkCrusher(final CraftworkCrusher card) {
        super(card);
    }

    @Override
    public CraftworkCrusher copy() {
        return new CraftworkCrusher(this);
    }
}
