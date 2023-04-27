package mage.cards.a;

import mage.MageInt;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTargetAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AmethystDragon extends AdventureCard {

    public AmethystDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{4}{R}{R}", "Explosive Crystal", "{4}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Explosive Crystal
        // Explosive Crystal deals 4 damage divided as you choose among any number of targets.
        this.getSpellCard().getSpellAbility().addEffect(new DamageMultiEffect(4));
        this.getSpellCard().getSpellAbility().addTarget(new TargetAnyTargetAmount(4));
    }

    private AmethystDragon(final AmethystDragon card) {
        super(card);
    }

    @Override
    public AmethystDragon copy() {
        return new AmethystDragon(this);
    }
}
