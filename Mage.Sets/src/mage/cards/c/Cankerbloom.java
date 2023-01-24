package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetEnchantmentPermanent;

/**
 * @author TheElk801
 */
public final class Cankerbloom extends CardImpl {

    public Cankerbloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {1}, Sacrifice Cankerbloom: Choose one --
        // * Destroy target artifact.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(1));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetArtifactPermanent());

        // * Destroy target enchantment.
        ability.addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetEnchantmentPermanent()));

        // * Proliferate.
        ability.addMode(new Mode(new ProliferateEffect(false)));
        this.addAbility(ability);
    }

    private Cankerbloom(final Cankerbloom card) {
        super(card);
    }

    @Override
    public Cankerbloom copy() {
        return new Cankerbloom(this);
    }
}
