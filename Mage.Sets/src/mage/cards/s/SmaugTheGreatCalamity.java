package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SmaugTheGreatCalamity extends AdventureCard {

    public SmaugTheGreatCalamity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{5}{R}{R}", "Spew Flame", "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Spew Flame
        // Spew Flame deals 5 damage to target creature.
        this.getSpellCard().getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        this.finalizeAdventure();
    }

    private SmaugTheGreatCalamity(final SmaugTheGreatCalamity card) {
        super(card);
    }

    @Override
    public SmaugTheGreatCalamity copy() {
        return new SmaugTheGreatCalamity(this);
    }
}
